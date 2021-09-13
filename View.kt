        buttonSendAnalysis.setOnClickListener {
            if (ccbCdc.isChecked() && ccbTerms.isChecked()) {
                if ((isEntryMandatory && entryValidate()) ||
                    isEntryMandatory.not()
                ) {
                    viewCheckoutBookletPaymentInstallments.gone()

                    selectedInstallmentOption?.let { installment ->
                        checkoutBookletPaymentViewModel.sendAnalysis(
                            installment.entryValue, installment.installmentQuantity
                        )
                    }
                    viewCheckoutBookletPaymentAnalysisLoad.visible()
                } else {
                    activity?.showAlertDialog(
                        getString(R.string.fragment_checkout_booklet_payment_entry_need)
                    )
                }
            } else {
                activity?.showAlertDialog(
                    getString(R.string.fragment_checkout_booklet_payment_terms_need)
                )
            }
        }
