package com.myteam.tts_connected_claims;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class serviceProviderLiaison implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label(value = "contactDescription")
	private java.lang.String contactDescription;
	@org.kie.api.definition.type.Label(value = "contactNumber")
	private java.lang.String contactNumber;
	@org.kie.api.definition.type.Label(value = "representative")
	private com.myteam.tts_connected_claims.representative representative;

	public serviceProviderLiaison() {
	}

	public java.lang.String getContactDescription() {
		return this.contactDescription;
	}

	public void setContactDescription(java.lang.String contactDescription) {
		this.contactDescription = contactDescription;
	}

	public java.lang.String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(java.lang.String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public com.myteam.tts_connected_claims.representative getRepresentative() {
		return this.representative;
	}

	public void setRepresentative(
			com.myteam.tts_connected_claims.representative representative) {
		this.representative = representative;
	}

	public serviceProviderLiaison(java.lang.String contactDescription,
			java.lang.String contactNumber,
			com.myteam.tts_connected_claims.representative representative) {
		this.contactDescription = contactDescription;
		this.contactNumber = contactNumber;
		this.representative = representative;
	}

}