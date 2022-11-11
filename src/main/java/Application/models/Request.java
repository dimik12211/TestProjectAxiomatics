package Application.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "Request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CreditAmount")
    private String CreditAmount;
    @Column(name = "Status")
    private boolean Status;
    @Column(name = "CreditTerm")
    private int CreditTerm;
    @Column(name = "ApprovedLoanAmount")
    private int ApprovedLoanAmount;
    @Column(name = "PeriodOfWork")
    private String PeriodOfWork;
    @Column(name = "Position")
    private String Position;
    @Column(name = "NameOfTheOrganization")
    private String NameOfTheOrganization;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Client_id")
    private Client OneToManyClient;
    @OneToOne(mappedBy = "OneToOneRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreditAgreement RequestToCreditAgreement;

    public Request() {
    }

    public Request(String CreditAmount, boolean Status, int CreditTerm, int ApprovedLoanAmount, String PeriodOfWork, String Position, String NameOfTheOrganization) {
        try {
            this.CreditAmount = CreditAmount;
            this.Status = Status;
            this.CreditTerm = CreditTerm;
            this.ApprovedLoanAmount = ApprovedLoanAmount;
            this.PeriodOfWork = PeriodOfWork;
            this.Position = Position;
            this.NameOfTheOrganization = NameOfTheOrganization;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.CreditAmount = CreditAmount;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        this.Status = status;
    }

    public int getCreditTerm() {
        return CreditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.CreditTerm = creditTerm;
    }

    public int getApprovedLoanAmount() {
        return ApprovedLoanAmount;
    }

    public void setApprovedLoanAmount(int approvedLoanAmount) {
        this.ApprovedLoanAmount = approvedLoanAmount;
    }

    public String getPeriodOfWork() {
        return PeriodOfWork;
    }

    public void setPeriodOfWork(String periodOfWork) {
        this.PeriodOfWork = periodOfWork;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        this.Position = position;
    }

    public String getNameOfTheOrganization() {
        return NameOfTheOrganization;
    }

    public void setNameOfTheOrganization(String nameOfTheOrganization) {
        this.NameOfTheOrganization = nameOfTheOrganization;
    }

    public Client getOneToManyClient() {
        return OneToManyClient;
    }

    public void setOneToManyClient(Client client) {
        this.OneToManyClient = client;
    }

    public CreditAgreement getRequestToCreditAgreement() {
        return RequestToCreditAgreement;
    }

    public void setRequestToCreditAgreement(CreditAgreement creditAgreement) {
        this.RequestToCreditAgreement = creditAgreement;
    }
}
