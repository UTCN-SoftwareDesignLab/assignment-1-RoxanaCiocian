package model;


import java.sql.Date;

public class Account {

    private Long id;
    private String ident_number;
    private String type;
    private float balance;
    private Date creation_date;
    private Long id_client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdent_number() {
        return ident_number;
    }

    public void setIdent_number(String ident_number) {
        this.ident_number = ident_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", ident_number='" + ident_number + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", creation_date=" + creation_date +
                ", id_client=" + id_client +
                '}';
    }
}
