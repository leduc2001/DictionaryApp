package group11.dictapp;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

enum SpeakerGender {
    male, female
}
public class textToSpeech {
    SpeakerGender speakerGender = SpeakerGender.male;
    Synthesizer synthesizer;

    public textToSpeech() {
        try {
            //Gender not working for some reason
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral(
                    "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();}
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void speakWord(String word) {

        try {

            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(word, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

            // Deallocate the Synthesizer.
            //synthesizer.deallocate();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSpeakerGender(SpeakerGender speakerGender) {
        this.speakerGender = speakerGender;
    }

}