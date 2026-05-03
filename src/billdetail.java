public class billdetail {
    
    int billdetailid = 0;
    int billid = 0;
    String pname = "";
    int price = 0;
    int quantity =0;
    
    public billdetail(int billdetailid, int billid, String pname, int price,int quantity ){
        
        this.billdetailid = billdetailid;
        this.billid = billid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        
    }
    
}