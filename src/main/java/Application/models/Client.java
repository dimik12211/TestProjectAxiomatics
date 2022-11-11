package Application.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "FirstName")
    private String FirstName;
    @Column(name = "LastName")
    private String LastName;
    @Column(name = "Patronymic")
    private String Patronymic;
    @Column(name = "PassportData")
    private String PassportData;
    @Column(name = "FamilyStatus")
    private String FamilyStatus;
    @Column(name = "Address")
    private String Address;
    @Column(name = "Number")
    private String Number;
    @OneToMany(mappedBy = "OneToManyClient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Request> Requests;

    public Client() {
    }

    public Client(String FirstName, String LastName, String Patronymic, String PassportData, String FamilyStatus, String Address, String Number) {
        try {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Patronymic = Patronymic;
            this.PassportData = PassportData;
            this.FamilyStatus = FamilyStatus;
            this.Address = Address;
            this.Number = Number;
            Requests = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeRequest(Request request) {
        Requests.remove(request);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.Patronymic = patronymic;
    }

    public String getPassportData() {
        return PassportData;
    }

    public void setPassportData(String passportData) {
        this.PassportData = passportData;
    }

    public String getFamilyStatus() {
        return FamilyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.FamilyStatus = familyStatus;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public List<Request> getRequests() {
        return Requests;
    }

    public void setRequests(List<Request> request) {
        this.Requests = request;
    }

    public void addRequest(Request request, Client client) {
        try {
            request.setOneToManyClient(client);
            Requests.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

