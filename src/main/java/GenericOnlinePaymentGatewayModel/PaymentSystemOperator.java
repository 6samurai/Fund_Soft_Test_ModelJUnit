package GenericOnlinePaymentGatewayModel;

public class PaymentSystemOperator {

    private  boolean offVerif = false;
    private  boolean auth = false;
    private  boolean capture = false;
    private  boolean refund = false;
    private  boolean voided = false;
    private  boolean invalid = true;

    boolean isOfflineVerified() {return  offVerif;}

    boolean isAuthorised() {return  auth;}

    boolean isCaptured() {return  capture;}

    boolean isRefunded() {return  refund;}

    boolean isVoid() {return  voided;}

    boolean isInvalid() {return  invalid;}


    void invalidOfflineVerif(){
        if(!offVerif && invalid){
            offVerif = false;
        }
    }

    void validOfflineVerif(){
        if(!offVerif && invalid){
            offVerif = true;
            invalid = false;
        }
    }


    void invalidAuth(){
        if(offVerif && !auth && !invalid){
            offVerif = false;
            invalid = true;
        }
    }

    void validAuth(){
        if(offVerif && !auth && !invalid){
            auth = true;
        }
    }

    void voidOperation(){
        if(offVerif && auth && !voided && !invalid ){
            voided = true;
        }
    }



    void invalidCapture(){
        if(offVerif && auth  && !invalid){
            offVerif = false;
            auth = false;
            invalid = true;
        }
    }

    void validCapture(){
        if(offVerif && auth && !capture && !voided  && !invalid ){
            capture = true;
        }
    }

    void invalidRefund(){
        if(offVerif && auth && capture && !voided  && !invalid){
            offVerif = false;
            auth = false;
            capture = false;
            invalid = true;
        }
    }

    void validRefund(){
        if(offVerif && auth && capture && !refund  && !invalid && !voided){
            refund = true;

        }
    }


}
