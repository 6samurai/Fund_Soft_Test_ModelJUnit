package GenericOnlinePaymentGatewayModel;
import GenericOnlinePaymentGatewayModel.enums.PaymentSystemOperatorStates;
import junit.framework.Assert;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GraphListener;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.Random;

public class PaymentSystemOperatorModelTest implements  FsmModel {
    private PaymentSystemOperatorStates modelState;
    private  boolean offVerif = false;
    private  boolean auth = false;
    private  boolean capture = false;
    private  boolean refund = false;
    private  boolean voided = false;
    private  boolean idle = true;
    private  PaymentSystemOperator sut;

    public PaymentSystemOperatorStates getState() {
        return modelState;
    }

    public void reset(final boolean b) {
        modelState = PaymentSystemOperatorStates.IDLE;
        offVerif = false;
        auth = false;
        capture = false;
        refund = false;
        voided = false;
        idle = true;
        if (b) {
            sut = new PaymentSystemOperator();
        }
    }


    public boolean invalidOfflineVerifyGuard() {
        return getState().equals(PaymentSystemOperatorStates.IDLE);
    }
    public @Action void invalidOfflineVerify() {
        sut.invalidOfflineVerif();
        idle = true;
        offVerif = false;
        modelState = PaymentSystemOperatorStates.IDLE;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
    }


    public boolean offlineVerifyGuard() {
        return getState().equals(PaymentSystemOperatorStates.IDLE);
    }
    public @Action void offlineVerify() {
        sut.validOfflineVerif();
        idle = false;
        offVerif = true;
        modelState = PaymentSystemOperatorStates.OFFLINE_VERIF;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
    }

    public boolean invalidAuthGuard() {
        return getState().equals(PaymentSystemOperatorStates.OFFLINE_VERIF);
    }
    public @Action void invalidAuth() {
        sut.invalidAuth();
        idle = true;
        offVerif = false;
        auth = false;
        modelState = PaymentSystemOperatorStates.IDLE;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
    }

    public boolean validAuthGuard() {
        return getState().equals(PaymentSystemOperatorStates.OFFLINE_VERIF);
    }
    public @Action void validAuth() {
        sut.validAuth();
        idle = false;
        offVerif = true;
        auth = true;
        modelState = PaymentSystemOperatorStates.AUTH;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
    }


    public boolean voidCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);
    }
    public @Action void voidCapture() {
        sut.voidOperation();
        voided = true;
        modelState = PaymentSystemOperatorStates.VOID;

        Assert.assertEquals("The model's void state doesn't match the SUT's state.", voided, sut.isVoid());
    }

    public boolean invalidCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);
    }
    public @Action void invalidCapture() {
        sut.invalidCapture();
        idle = true;
        offVerif = false;
        auth = false;
        capture = false;
        modelState = PaymentSystemOperatorStates.IDLE;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
        Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
    }

    public boolean validCaptureGuard() {
        return getState().equals(PaymentSystemOperatorStates.AUTH);
    }
    public @Action void validCapture() {
        sut.validCapture();
        idle = false;
        offVerif = true;
        auth = true;
        capture = true;
        modelState = PaymentSystemOperatorStates.CAPUTRE;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
          Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
    }

    public boolean invalidRefundGuard() {
        return getState().equals(PaymentSystemOperatorStates.CAPUTRE);
    }
    public @Action void invalidRefund() {
        sut.invalidRefund();
        idle = true;
        offVerif = false;
        auth = false;
        capture = false;
        refund = false;
        modelState = PaymentSystemOperatorStates.IDLE;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
        Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
        Assert.assertEquals("The model's refund state doesn't match the SUT's state.", refund, sut.isRefunded());

    }

    public boolean validRefundGuard() {
        return getState().equals(PaymentSystemOperatorStates.CAPUTRE);
    }
    public @Action void validRefund() {
        sut.validRefund();
        idle = false;
        offVerif = true;
        auth = true;
        capture = true;
        refund = true;
        modelState = PaymentSystemOperatorStates.REFUND;

        Assert.assertEquals("The model's idle state doesn't match the SUT's state.", idle, sut.isIdle());
        Assert.assertEquals("The model's offline Verified state doesn't match the SUT's state.", offVerif, sut.isOfflineVerified());
        Assert.assertEquals("The model's authorised state doesn't match the SUT's state.", auth, sut.isAuthorised());
        Assert.assertEquals("The model's capture state doesn't match the SUT's state.", capture, sut.isCaptured());
        Assert.assertEquals("The model's refund state doesn't match the SUT's state.", refund, sut.isRefunded());
    }



    @Test
    public void TelephoneSystemModelRunner() throws FileNotFoundException {
        final Tester tester = new GreedyTester(new PaymentSystemOperatorModelTest());
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
