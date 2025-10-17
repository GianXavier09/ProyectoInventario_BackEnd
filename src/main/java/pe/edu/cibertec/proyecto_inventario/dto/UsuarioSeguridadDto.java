package pe.edu.cibertec.proyecto_inventario.dto;

public class UsuarioSeguridadDto {

	
	    private Integer idusuario;
	    private String nomusuario;
	    private String token;
	    private String mensajeError;

	    public UsuarioSeguridadDto(Integer idusuario, String nomusuario, String token, String mensajeError) {
	        this.idusuario = idusuario;
	        this.nomusuario = nomusuario;
	        this.token = token;
	        this.mensajeError = mensajeError;
	    }

		public Integer getIdusuario() {
			return idusuario;
		}

		public void setIdusuario(Integer idusuario) {
			this.idusuario = idusuario;
		}

		public String getNomusuario() {
			return nomusuario;
		}

		public void setNomusuario(String nomusuario) {
			this.nomusuario = nomusuario;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getMensajeError() {
			return mensajeError;
		}

		public void setMensajeError(String mensajeError) {
			this.mensajeError = mensajeError;
		}
}