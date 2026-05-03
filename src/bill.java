public class bill {
    int billid = 0;
    String datetime = "";
    int gtotal =0;
    String phoneno = "";
    String paymenttype = "";
    
    public bill(int billid, String datetime,  String phoneno, String paymenttype,int gtotal){
        this.billid = billid;
        this.datetime = datetime;
        this.phoneno = phoneno;
        this.paymenttype = paymenttype;
        this.gtotal = gtotal;
    }
    
}