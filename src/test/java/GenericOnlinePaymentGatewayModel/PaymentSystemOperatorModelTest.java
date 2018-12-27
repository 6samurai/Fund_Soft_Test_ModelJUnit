package GenericOnlinePaymentGatewayModel;

import GenericOnlinePaymentGatewayModel.enums.PaymentSystemOperatorStates;
import junit.framework.Assert;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Random;

public class PaymentSystemOperatorModelTest implements FsmModel {

    //Variables
    private PaymentSystemOperatorStates modelState = PaymentSystemOperatorStates.INVALID;
    private boolean offVerif = false;
    private boolean auth = false;
    private boolean capture = false;
    private boolean refund = false;
    private boolean voided = false;
    private boolean invalid = true;

    //SUT
    private PaymentSystemOperator sut = new PaymentSystemOperator();

    //Method implementations
    public PaymentSystemOperatorStates getState() {
        return modelState;
    }

    public void reset(final boolean reset) {
        modelState = PaymentSystemOperatorStates.INVALID;
        offVerif = false;
        auth = false;
        capture = false;
        refund = false;
        voided = false;
        invalid = true;
        if (reset) {
            sut = new PaymentSystemOperator();
        }
    }


    public boolean invalidOfflineVerifyGuard() {
        return getState().equals(PaymentSystemOperatorStates.INVALID);
    }

    public @Action
    void invalidOfflineVerify() {
        sut.invalidOfflineVerif();
        invalid = true;
        offVerif = false;
        modelState = PaymentSystemOperatorStates.INVALID;

        Assert.assertEquals("The model's invalid state doesn't match the SUT's state.", invalid, sut.isInvalid());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
    }


    public boolean offlineVerifyGuard() {
        return getState().equals(PaymentSystemOperatorStates.INVALID);
    }

    public @Action
    void offlineVerify() {
        sut.validOfflineVerif();
        invalid = false;
        offVerif = true;
        modelState = PaymentSystemOperatorStates.OFFLINE_VERIF;

        Assert.assertEquals("The model's invalid state doesn't match the SUT's state.", invalid, sut.isInvalid());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
    }

    public boolean invalidAuthGuard() {
        return getState().equals(PaymentSystemOperatorStates.OFFLINE_VERIF);
    }

    public @Action
    void invalidAuth() {
        sut.invalidAuth();
        invalid = true;
        offVerif = false;
        modelState = PaymentSystemOperatorStates.INVALID;

        Assert.assertEquals("The model's invalid state doesn't match the SUT's state.", invalid, sut.isInvalid());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
   }

    public boolean validAuthGuard() {
        return getState().equals(PaymentSystemOperatorStates.OFFLINE_VERIF);
    }

    public @Action
    void validAuth() {
        sut.validAuth();
        auth = true;
        modelState = PaymentSystemOperatorStates.AUTH;

        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
    }

   public boolean invalidCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);
    }

    public @Action
    void invalidCapture() {
        sut.invalidCapture();
        invalid = true;
        offVerif = false;
        auth = false;
        modelState = PaymentSystemOperatorStates.INVALID;

        Assert.assertEquals("The model's invalid state doesn't match the SUT's state.", invalid, sut.isInvalid());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
    }

   public boolean validCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);

    }

    public @Action
    void validCapture() {
        sut.validCapture();

        capture = true;
        modelState = PaymentSystemOperatorStates.CAPTURE;
        Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
 }


    public boolean voidCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);

    }

    public @Action
    void voidCapture() {
        sut.voidOperation();

        voided = true;
        modelState = PaymentSystemOperatorStates.VOID;

        Assert.assertEquals("The model's void state doesn't match the SUT's state.", voided, sut.isVoid());
    }


    public boolean invalidRefundGuard() {
        return getState().equals(PaymentSystemOperatorStates.CAPTURE);
    }

    public @Action
    void invalidRefund() {
        sut.invalidRefund();
        invalid = true;
        offVerif = false;
        auth = false;
        capture = false;

        modelState = PaymentSystemOperatorStates.INVALID;

        Assert.assertEquals("The model's invalid state doesn't match the SUT's state.", invalid, sut.isInvalid());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
        Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
    }

   public boolean validRefundGuard() {
        return getState().equals(PaymentSystemOperatorStates.CAPTURE);
    }

    public @Action
    void validRefund() {
        sut.validRefund();

        refund = true;

        modelState = PaymentSystemOperatorStates.REFUND;

        Assert.assertEquals("The model's refund state doesn't match the SUT's state.", refund, sut.isRefunded());
 }

    @Test
    public void TelephoneSystemModelRunner() throws FileNotFoundException {
        PaymentSystemOperatorModelTest myModel = new PaymentSystemOperatorModelTest();
        //greedy prefers untested nodes
        //random pure random
      //   final Tester tester = new GreedyTester(myModel);
  //      final Tester tester = new LookaheadTester(myModel);
       //   ((LookaheadTester) tester).setDepth(4);


         final Tester tester  = new RandomTester(myModel);
        tester.setRandom(new Random());
        final GraphListener graphListener = tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());

        tester.generate(500);
        tester.printCoverage();
    }
}
