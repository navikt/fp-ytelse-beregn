package no.nav.foreldrepenger.ytelse.beregning.regelmodell.regelmodell.fastsett;

import java.util.Objects;
import java.util.UUID;

import no.nav.foreldrepenger.ytelse.beregning.regelmodell.beregningsgrunnlag.AktivitetStatus;
import no.nav.foreldrepenger.ytelse.beregning.regelmodell.beregningsgrunnlag.Arbeidsforhold;
import no.nav.foreldrepenger.ytelse.beregning.regelmodell.beregningsgrunnlag.BeregningsgrunnlagPrStatus;
import no.nav.foreldrepenger.ytelse.beregning.regelmodell.uttakresultat.UttakAktivitet;

public final class BeregningsgrunnlagUttakArbeidsforholdMatcher {

    private BeregningsgrunnlagUttakArbeidsforholdMatcher() {
        // Skjuler default
    }

    private record ArbeidsforholdRef(UUID referanse) {
        private static ArbeidsforholdRef ref(String referanse) {
            return referanse == null ? NULL_OBJECT : new ArbeidsforholdRef(UUID.fromString(referanse));
        }

        public boolean gjelderFor(ArbeidsforholdRef ref) {
            if (referanse() == null || ref.referanse() == null) {
                return true;
            }
            return Objects.equals(referanse(), ref.referanse());
        }
    }

    private static final ArbeidsforholdRef NULL_OBJECT = new ArbeidsforholdRef(null);



    public static boolean matcherArbeidsforhold(Arbeidsforhold arbeidsforholdUttak, Arbeidsforhold arbeidsforholdBeregning) {
        if (arbeidsforholdBeregning == null || arbeidsforholdUttak == null) {
            // begge må være null for at de skal være like
            return Objects.equals(arbeidsforholdBeregning, arbeidsforholdUttak);
        }
        var bgRef = ArbeidsforholdRef.ref(arbeidsforholdBeregning.arbeidsforholdId());
        var uttakRef = ArbeidsforholdRef.ref(arbeidsforholdUttak.arbeidsforholdId());
        return Objects.equals(arbeidsforholdBeregning.frilanser(), arbeidsforholdUttak.frilanser())
            && Objects.equals(arbeidsforholdBeregning.identifikator(), arbeidsforholdUttak.identifikator())
            && bgRef.gjelderFor(uttakRef);
    }


    public static boolean matcherGenerellAndel(BeregningsgrunnlagPrStatus beregningsgrunnlagPrStatus, UttakAktivitet aktivitet) {
        return aktivitet.aktivitetStatus().equals(beregningsgrunnlagPrStatus.aktivitetStatus())
            || aktivitet.aktivitetStatus().equals(AktivitetStatus.ANNET) && !beregningsgrunnlagPrStatus.aktivitetStatus().erGraderbar();
    }


}
