package Application.models;

import javax.persistence.*;

@Entity
@Table(name = "CreditAgreement")
public class CreditAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DateOfSignature")
    private String DateOfSignature;

    @Column(name = "Signed")
    private boolean Signed;

    public CreditAgreement() {
    }

    public CreditAgreement(String DateOfSignature, boolean Signed) {
        try {
            this.DateOfSignature = DateOfSignature;
            this.Signed = Signed;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Request_id")
    private Request OneToOneRequest;

    public int getId() {
        return id;
    }

    public Request getOneToOneRequest() {
        return OneToOneRequest;
    }

    public void setOneToOneRequest(Request setRequest) {
        this.OneToOneRequest = setRequest;
    }

    public String getDateOfSignature() {
        return DateOfSignature;
    }

    public void setDateOfSignature(String dateOfSignature) {
        this.DateOfSignature = dateOfSignature;
    }

    public boolean getSigned() {
        return Signed;
    }

    public void setSigned(boolean signed) {
        this.Signed = signed;
    }
}
