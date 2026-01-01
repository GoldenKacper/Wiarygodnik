import { SentimentType } from "../domain/SentimentType";

export const mapSentimentType = (status: string) => {
    switch (status) {
        case SentimentType.POSITIVE:
            return "Pozytywny";
        case SentimentType.NEGATIVE:
            return "Negatywny";
        case SentimentType.NEUTRAL:
            return "Neutralny";
        case SentimentType.ALARMIST:
            return "Alarmistyczny";
        case SentimentType.IRONIC:
            return "Ironiczny";
        case SentimentType.PERSUASIVE:
            return "Perswazyjny";
        case SentimentType.AGGRESSIVE:
            return "Agresywny";
        case SentimentType.FORMAL:
            return "Formalny";
        default:
            return "Nieznany typ sentymentu";
    }
};