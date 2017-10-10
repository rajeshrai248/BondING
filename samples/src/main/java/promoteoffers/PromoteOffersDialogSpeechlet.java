//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package promoteoffers;

import accountretriever.IIntentHandler;
import accountretriever.IntentHandler;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromoteOffersDialogSpeechlet implements SpeechletV2 {
    private static final Logger log = LoggerFactory.getLogger(PromoteOffersDialogSpeechlet.class);
    private static final String WELCOME_TEXT = "Welcome to the ING! How may I help you?";
    private static final String HELP_TEXT = "You can use this skill by asking something like: show me my balance.";
    private static final String UNHANDLED_TEXT = "This is unsupported. Please ask something else.";
    private static final String ERROR_TEXT = "There was an error with the BondING skill. Please try again.";
    public static final String PROMOTE_OFFERS_INTENT = "PromoteOffersIntent";
    private final IntentHandler intentHandler;

    @Inject
    public PromoteOffersDialogSpeechlet(@Named("PromoteOffersIntent") IIntentHandler planMyTripIntentHandler) {
        this.intentHandler = IntentHandler.builder().with(PROMOTE_OFFERS_INTENT, planMyTripIntentHandler).with("AMAZON.HelpIntent", (request) -> {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(HELP_TEXT);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(outputSpeech);
            return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
        }).withDefault((request) -> {
            log.info("Unhandled Intent {}", ((IntentRequest) request.getRequest()).getIntent().getName());
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(UNHANDLED_TEXT);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(outputSpeech);
            return SpeechletResponse.newTellResponse(outputSpeech);
        }).build();
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
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(WELCOME_TEXT);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {
        IntentRequest intentRequest = speechletRequestEnvelope.getRequest();
        Session session = speechletRequestEnvelope.getSession();
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(), session.getSessionId());

        try {
            return this.intentHandler.handle(speechletRequestEnvelope);
        } catch (SpeechletRequestHandlerException var7) {
            log.error("Failed to handle intent", var7);
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(ERROR_TEXT);
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
