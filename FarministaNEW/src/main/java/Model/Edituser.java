package Model;

public class Edituser {
    private int no; // Nomor urut untuk TableView
    private int id_petani;
    private String user;
    private String password;
    private String role;

    // Constructor lengkap untuk kebutuhan TableView
    public Edituser(int id_petani, String user, String password, String role) {  
           this.id_petani = id_petani; // Pastikan ini diatur dengan benar  
           this.user = user;  
           this.password = password;  
           this.role = role;  
       }  
    public Edituser(int no, int id_petani, String user, String password, String role) {  
        this.no = no;  
        this.id_petani = id_petani; // Pastikan id_petani diinisialisasi  
        this.user = user;  
        this.password = password;  
        this.role = role;  
    }  
       

    // Getter dan Setter
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getIdPetani() {
        return id_petani;
    }

    
    public String getUsername() {
        return user;
    }

    public void setUsername(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}