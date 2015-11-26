package crysxd.de.bankaccountviewer;

import me.figo.FigoSession;

/**
 * Created by cwuer on 11/26/15.
 */
public class FigoSessionHolder {

    private final static FigoSession mSession = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");

    public static FigoSession i() {
        return mSession;

    }
}
