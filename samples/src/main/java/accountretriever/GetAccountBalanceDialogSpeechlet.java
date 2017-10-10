//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package accountretriever;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import promoteoffers.PromoteOffersNoDelegateIntentHandler;

public class GetAccountBalanceDialogSpeechlet implements SpeechletV2 {
    public static final String GET_ACCOUNT_BALANCE_INTENT = "GetAccountBalanceNewIntent";
    private static final Logger log = LoggerFactory.getLogger(GetAccountBalanceDialogSpeechlet.class);
    private static final String WELCOME_TEXT = "Welcome to the ING! How may I help you?";
    private static final String HELP_TEXT = "You can use this skill by asking something like: show me my balance.";
    private static final String UNHANDLED_TEXT = "This is unsupported. Please ask something else.";
    private static final String ERROR_TEXT = "There was an error with the BondING skill. Please try again.";

    public GetAccountBalanceDialogSpeechlet() {
    }

    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> speechletRequestEnvelope) {
        SessionStartedRequest sessionStartedRequest = speechletRequestEnvelope.getRequest();
        Session session = speechletRequestEnvelope.getSession();
        log.info("onSessionStarted requestId={}, sessionId={}", sessionStartedRequest.getRequestId(), session.getSessionId());
    }

    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> speechletRequestEnvelope) {
        LaunchRequest launchRequest = speechletRequestEnvelope.getRequest();
        Session session = speechletRequestEnvelope.getSession();
        log.info("onLaunch requestId={}, sessionId={}", launchRequest.getRequestId(), session.getSessionId());
        log.info("received request for {}", speechletRequestEnvelope.getSession().getUser().getUserId());
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Welcome to the ING! How may I help you?");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {
        IntentRequest intentRequest = speechletRequestEnvelope.getRequest();
        Session session = speechletRequestEnvelope.getSession();
        log.info("received request for {}", speechletRequestEnvelope.getSession().getUser().getUserId());
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(), session.getSessionId());

        try {
            log.info("intent name {}", intentRequest.getIntent().getName());
            return intentRequest.getIntent().getName().equalsIgnoreCase("PromoteOffersIntent") ? (new PromoteOffersNoDelegateIntentHandler()).handle(speechletRequestEnvelope) : (new GetAccountBalanceNoDelegateIntentHandler()).handle(speechletRequestEnvelope);
        } catch (SpeechletRequestHandlerException var7) {
            log.error("Failed to handle intent", var7);
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("There was an error with the BondING skill. Please try again.");
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(outputSpeech);
            return SpeechletResponse.newTellResponse(outputSpeech);
        }
    }

    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> speechletRequestEnvelope) {
        SessionEndedRequest sessionEndedRequest = speechletRequestEnvelope.getRequest();
        Session session = speechletRequestEnvelope.getSession();
        log.info("onSessionEnded requestId={}, sessionId={}", sessionEndedRequest.getRequestId(), session.getSessionId());
    }
}
