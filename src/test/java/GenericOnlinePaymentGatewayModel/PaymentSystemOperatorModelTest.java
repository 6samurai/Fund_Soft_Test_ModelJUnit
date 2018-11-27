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
        tester.generate(250);
        tester.printCoverage();
    }
}
