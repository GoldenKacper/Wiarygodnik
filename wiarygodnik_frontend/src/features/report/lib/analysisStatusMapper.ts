import { AnalysisStatus } from "../domain/AnalysisStatus";

export const mapAnalysisStatus = (status: string) => {
    switch (status) {
        case AnalysisStatus.ANALYSISNG_CONTENT:
            return "Analizowanie treści źródła";
        case AnalysisStatus.COMPARING_SIMILAR_SOURCES:
            return "Porównywanie z podobnymi źródłami";
        case AnalysisStatus.COMPLETED:
            return "Analiza zakończona";
        case AnalysisStatus.GENERATING:
            return "Generowanie raportu";
        case AnalysisStatus.GENERATED:
            return "Raport wygenerowany";
        case AnalysisStatus.FAILED:
            return "Analiza nie powiodła się";
        default:
            return "Nieznany status";
    }
};