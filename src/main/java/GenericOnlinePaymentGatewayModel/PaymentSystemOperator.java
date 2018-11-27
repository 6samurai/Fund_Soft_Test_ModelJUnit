package GenericOnlinePaymentGatewayModel;

public class PaymentSystemOperator {

    private  boolean offVerif = false;
    private  boolean auth = false;
    private  boolean capture = false;
    private  boolean refund = false;
    private  boolean voided = false;

    boolean isOfflineVerified() {return  offVerif;}

    boolean isAuthenticated() {return  auth;}

    boolean isCaptured() {return  capture;}

    boolean isRefunded() {return  refund;}

    boolean isVoid() {return  voided;}
    //
    void validOfflineVerif(){
        if(!offVerif && !auth && !capture && !refund && !voided){
            offVerif = true;
        }
    }

  /*  void invalidOfflineVerif(){
        if(!offVerif && !auth && !capture && !refund){
            offVerif = false;
        }
    }*/

    void invalidAuth(){
        if(offVerif && !auth && !capture && !refund && !voided){
            offVerif = false;
        }
    }

    void validAuth(){
        if(offVerif && !auth && !capture && !refund && !voided){
            offVerif = true;
        }
    }

    void invalidCapture(){
        if(offVerif && auth && !capture && !refund && !voided){
            offVerif = false;
            auth = false;
        }
    }

    void notCapturedinWeek(){
        if(offVerif && auth && !capture && !refund && !voided){
            voided = true;
        }
    }

    void validCapture(){
        if(offVerif && auth && !capture && !refund && !voided){
            capture = true;
        }
    }

    void invalidRefund(){
        if(offVerif && auth && capture && !refund && !voided){
            offVerif = false;
            auth = false;
            capture = false;
        }
    }

    void validRefund(){
        if(offVerif && auth && capture && !refund && !voided){
            refund = true;
        }
    }
}
