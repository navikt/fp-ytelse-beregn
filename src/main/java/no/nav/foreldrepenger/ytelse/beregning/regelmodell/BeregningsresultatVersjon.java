package no.nav.foreldrepenger.ytelse.beregning.regelmodell;

import no.nav.fpsak.nare.evaluation.summary.EvaluationVersion;
import no.nav.fpsak.nare.evaluation.summary.NareVersion;

public class BeregningsresultatVersjon {

    private BeregningsresultatVersjon() {
    }

    public static final EvaluationVersion BEREGNINGSRESULTAT_VERSJON = NareVersion.readVersionPropertyFor("fp-ytelse-beregn", "nare/fp-ytelse-beregn-version.properties");


}
