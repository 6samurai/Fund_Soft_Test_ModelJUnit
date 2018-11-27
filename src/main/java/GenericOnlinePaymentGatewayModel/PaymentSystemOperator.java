package GenericOnlinePaymentGatewayModel;

public class PaymentSystemOperator {

    private  boolean offVerif = false;
    private  boolean auth = false;
    private  boolean capture = false;
    private  boolean refund = false;
    private  boolean voided = false;
    private  boolean idle = true;

    boolean isOfflineVerified() {return  offVerif;}

    boolean isAuthorised() {return  auth;}

    boolean isCaptured() {return  capture;}

    boolean isRefunded() {return  refund;}

    boolean isVoid() {return  voided;}

    boolean isIdle() {return  idle;}
    //
    void validOfflineVerif(){
        if(!offVerif && !auth && !capture && !refund && !voided && idle){
            offVerif = true;
            idle = false;
        }
    }

    void invalidOfflineVerif(){
        if(!offVerif && !auth && !capture && !refund && idle){
            offVerif = false;
        }
    }

    void invalidAuth(){
        if(offVerif && !auth && !capture && !refund && !voided && !idle){
            offVerif = false;
            idle = true;
        }
    }

    void validAuth(){
        if(offVerif && !auth && !capture && !refund && !voided && !idle){
            auth = true;
        }
    }

    void invalidCapture(){
        if(offVerif && auth && !capture && !refund && !voided && !idle){
            offVerif = false;
            auth = false;
            idle = true;
        }
    }

    void voidOperation(){
        if(offVerif && auth && !capture && !refund && !voided && !idle){
            voided = true;
        }
    }

    void validCapture(){
        if(offVerif && auth && !capture && !refund && !voided && !idle){
            capture = true;
        }
    }

    void invalidRefund(){
        if(offVerif && auth && capture && !refund && !voided && !idle){
            offVerif = false;
            auth = false;
            capture = false;
            idle = true;
        }
    }

    void validRefund(){
        if(offVerif && auth && capture && !refund && !voided && !idle){
            refund = true;
        }
    }
}
