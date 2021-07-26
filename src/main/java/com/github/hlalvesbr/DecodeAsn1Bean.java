package com.github.hlalvesbr;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.primefaces.model.file.UploadedFile;

@Named
@ViewScoped
public class DecodeAsn1Bean implements Serializable {

    private UploadedFile asn1File;
    private String asn1Dump;
    
    public void prepare() {

    }

    public void decode() {
        try {
            ASN1InputStream asn1InputStream = new ASN1InputStream(asn1File.getContent());
            ASN1Primitive asn1Primitive;
            StringBuilder stringBuilder = new StringBuilder(asn1File.getFileName() + "\n\n");

            while ((asn1Primitive = asn1InputStream.readObject()) != null) {
                stringBuilder.append(ASN1Dump.dumpAsString(asn1Primitive, true)).append("\n");
            }

            asn1Dump = stringBuilder.toString();
        } catch (Exception ex) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ex.getMessage() + "");
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }

    public UploadedFile getAsn1File() {
        return asn1File;
    }

    public void setAsn1File(UploadedFile asn1File) {
        this.asn1File = asn1File;
    }

    public String getAsn1Dump() {
        return asn1Dump;
    }

    public void setAsn1Dump(String asn1Dump) {
        this.asn1Dump = asn1Dump;
    }
}
