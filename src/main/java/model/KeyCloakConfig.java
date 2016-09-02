package model;

/**
 * Created by totvs on 02/09/2016.
 */
public class KeyCloakConfig {
    private String grant_type;

    private String client_id;

    private String client_secret;


    private String url_servidor;

    private String realm;

    private String project_service_url;

    /**
     *@return the grant_type cf
     */
    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    /**
     *@return the client_id created on keycloak
     */
    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    /**
     *@return  the client_secret of your client @see keycloak
     */
    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    /**
     *@return Url of keycloak server
     */
    public String getUrl_servidor() {
        return url_servidor;
    }

    public void setUrl_servidor(String url_servidor) {
        this.url_servidor = url_servidor;
    }

    /**
     *
     * @return The Realm of your client_id
     */
    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * @return Url of your project rest teste server @see resteasy-core-keycloak
     */
    public String getProject_service_url() {
        return project_service_url;
    }

    public void setProject_service_url(String project_service_url) {
        this.project_service_url = project_service_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyCloakConfig)) return false;

        KeyCloakConfig that = (KeyCloakConfig) o;

        if (getGrant_type() != null ? !getGrant_type().equals(that.getGrant_type()) : that.getGrant_type() != null)
            return false;
        if (getClient_id() != null ? !getClient_id().equals(that.getClient_id()) : that.getClient_id() != null)
            return false;
        if (getClient_secret() != null ? !getClient_secret().equals(that.getClient_secret()) : that.getClient_secret() != null)
            return false;
        if (getUrl_servidor() != null ? !getUrl_servidor().equals(that.getUrl_servidor()) : that.getUrl_servidor() != null)
            return false;
        if (getRealm() != null ? !getRealm().equals(that.getRealm()) : that.getRealm() != null) return false;
        return getProject_service_url() != null ? getProject_service_url().equals(that.getProject_service_url()) : that.getProject_service_url() == null;

    }

    @Override
    public int hashCode() {
        int result = getGrant_type() != null ? getGrant_type().hashCode() : 0;
        result = 31 * result + (getClient_id() != null ? getClient_id().hashCode() : 0);
        result = 31 * result + (getClient_secret() != null ? getClient_secret().hashCode() : 0);
        result = 31 * result + (getUrl_servidor() != null ? getUrl_servidor().hashCode() : 0);
        result = 31 * result + (getRealm() != null ? getRealm().hashCode() : 0);
        result = 31 * result + (getProject_service_url() != null ? getProject_service_url().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KeyCloakConfig{");
        sb.append("grant_type='").append(grant_type).append('\'');
        sb.append(", client_id='").append(client_id).append('\'');
        sb.append(", client_secret='").append(client_secret).append('\'');
        sb.append(", url_servidor='").append(url_servidor).append('\'');
        sb.append(", realm='").append(realm).append('\'');
        sb.append(", project_service_url='").append(project_service_url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
