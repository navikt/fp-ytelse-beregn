package no.nav.foreldrepenger.ytelse.beregning.regelmodell;

import no.nav.foreldrepenger.ytelse.beregning.regelmodell.fastsett.RegelFastsettBeregningsresultat;
import no.nav.foreldrepenger.ytelse.beregning.regelmodell.feriepenger.BeregningsresultatFeriepengerRegelModell;
import no.nav.foreldrepenger.ytelse.beregning.regelmodell.feriepenger.RegelBeregnFeriepenger;
import no.nav.fpsak.nare.evaluation.summary.EvaluationSerializer;
import no.nav.fpsak.nare.evaluation.summary.NareVersion;
import no.nav.fpsak.nare.json.JsonOutput;
import no.nav.fpsak.nare.json.NareJsonException;

public final class BeregningsresultatRegler {

    private BeregningsresultatRegler() {
    }

    public static FastsattBeregningsresultat fastsettBeregningsresultat(BeregningsresultatGrunnlag grunnlag) {
        // Kalle regel
        var outputContainer = Beregningsresultat.opprett();
        var evaluation = new RegelFastsettBeregningsresultat().evaluer(grunnlag, outputContainer);
        var sporing = EvaluationSerializer.asJson(evaluation, NareVersion.NARE_VERSION, BeregningsresultatVersjon.BEREGNINGSRESULTAT_VERSJON);
        var versjon = BeregningsresultatVersjon.BEREGNINGSRESULTAT_VERSJON.version();

        // Map tilbake til domenemodell fra regelmodell
        return new FastsattBeregningsresultat(outputContainer, grunnlag, toJson(grunnlag), sporing, versjon);
    }

    public static FastsattFeriepengeresultat fastsettFeriepenger(BeregningsresultatFeriepengerGrunnlag grunnlag) {

        var regelInput = toJson(grunnlag);
        var mellomregning = new BeregningsresultatFeriepengerRegelModell(grunnlag);

        var evaluation = new RegelBeregnFeriepenger().evaluer(mellomregning);
        var sporing = EvaluationSerializer.asJson(evaluation, NareVersion.NARE_VERSION, BeregningsresultatVersjon.BEREGNINGSRESULTAT_VERSJON);
        var versjon = BeregningsresultatVersjon.BEREGNINGSRESULTAT_VERSJON.version();

        var resultat = new BeregningsresultatFeriepengerResultat(mellomregning.getBeregningsresultatFeriepengerPrÅrListe(), mellomregning.getFeriepengerPeriode());

        return new FastsattFeriepengeresultat(resultat, grunnlag, regelInput, sporing, versjon);
    }

    private static String toJson(Object grunnlag) {
        try {
            return JsonOutput.asJson(grunnlag);
        } catch (NareJsonException e) {
            throw new KontoRegelFeil("Kunne ikke serialisere regelinput for beregning av tilkjent/feriepenger.", e);
        }
    }

    public static class KontoRegelFeil extends RuntimeException {
        public KontoRegelFeil(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
