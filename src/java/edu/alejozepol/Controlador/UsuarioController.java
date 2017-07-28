package edu.alejozepol.Controlador;

import edu.alejozepol.entidades.Usuario;
import edu.alejozepol.Controlador.util.JsfUtil;
import edu.alejozepol.Controlador.util.PaginationHelper;
import edu.alejozepol.Beans.UsuarioFacade;
import edu.alejozepol.entidades.Rol;

import java.io.Serializable;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario usuario;
    private DataModel items = null;
    @EJB
    private edu.alejozepol.Beans.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Rol rolSeleccionado;
    private int documento;
    private String clave;

    public UsuarioController() {
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public Usuario getSelected() {
        if (usuario == null) {
            usuario = new Usuario();
            selectedItemIndex = -1;
        }
        return usuario;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        usuario = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        usuario = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        final String smtp = "smtp.gmail.com"; 
        final int puerto = 587; 
        final String usuarioCorreo = "palejozepol@gmail.com"; 
        final String contraseña = "Asdf1234$"; 
        try {
            getFacade().create(usuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("Parametros/Parametros").getString("UsuarioCreated"));
            Properties props = null;
            Session session = null;
            MimeMessage message = null;
            Address fromAddress = null;
            Address toAddress = null;
            props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtp);
            props.put("mail.smtp.port", puerto);
            
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuarioCorreo, contraseña);
                }
            });
            message = new MimeMessage(session);
            
            try {
                message.setContent("Cargue documentos usuario: "+  usuario.toString(), "text/plain");
                message.setSubject("el usuario:" + usuario.toString() + " acaba de cargar unos documentos" );
                fromAddress = new InternetAddress(usuario.getCorreo());
                message.setFrom(fromAddress);
                toAddress = new InternetAddress(usuario.getCorreo());
                message.setRecipient(Message.RecipientType.TO, toAddress);
                message.saveChanges();

                Transport transport = session.getTransport("smtp");
                transport.connect(smtp, puerto, usuarioCorreo, contraseña);
                if (!transport.isConnected()) {
                    return "emailFal";
                }
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

            } catch (MessagingException me) {
                return "emailFal";
            }

            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("Parametros/Parametros").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        usuario = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String update() {
        try {
            getFacade().edit(usuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("Parametros/Parametros").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("Parametros/Parametros").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        usuario = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(usuario);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("Parametros/Parametros").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("Parametros/Parametros").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            usuario = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }

    public String iniciarSesion() {
        String url = "";
        usuario = ejbFacade.login(documento, clave);
        FacesContext fc = FacesContext.getCurrentInstance();
        if (usuario != null) {
            rolSeleccionado = usuario.getRoles().get(0);
            url = "app/usuario/List.xhtml?faces-redirect=true";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Datos invalidos", "Documento o clave incorrectos");
            FacesMessage messageClv = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Clave invalida", "por favor verifique el valor ingresado");
            fc.addMessage("doc", message);
            fc.addMessage("clv", messageClv);
        }
        return url;
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        documento = 0;
        clave = "";
        usuario = null;
        return "/index.xhtml?faces-redirect=true";
    }

}
