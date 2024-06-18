package no.nav.foreldrepenger.ytelse.beregning.regelmodell.regelmodell;

import no.nav.fpsak.nare.evaluation.summary.EvaluationVersion;
import no.nav.fpsak.nare.evaluation.summary.NareVersion;

public class BeregningsresultatVersjon {

    private BeregningsresultatVersjon() {
    }

    public static final EvaluationVersion BEREGNINGSRESULTAT_VERSJON = NareVersion.readVersionPropertyFor("beregningsresultat", "nare/fp-ytelse-beregn-version.properties");


}
