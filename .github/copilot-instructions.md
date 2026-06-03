# fp-ytelse-beregn

Business-rule library for calculating foreldrepenger and svangerskapspenger with feriepenger.

## Shared context

- Source of truth for shared domain, architecture, and conventions: `navikt/fp-context`
- Copilot Space: `navikt/TeamForeldrepenger`

## Repo-specific context

| Topic      | Details                                                                    |
|------------|----------------------------------------------------------------------------|
| Role       | Combines beregning and uttak to a benefit result structure used downstream |
| Consumers  | `fp-sak` module `beregning-ytelse`                                         |
| Tech stack | Java SemVer library using `fp-nare` rule framework                         |
| API        | `BeregningsresultatRegler` with input / output objects                     |

The resulting structure of periods and shares/andeler with amounts and percentages is persisted
by `fp-sak` and used downstream for producing oppdrag to OS, statistics, benefit overlap detection, etc.

## Verification

- Verify behavior changes through `fp-sak` and relevant `navikt/fp-autotest` flows such as `fpsak` or `verdikjede`.
