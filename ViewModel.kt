fun sendAnalysis(entryValue: Double = 0.0, installmentQuantity: Int) {
        launch(
            enableLoading = false,
            errorBlock = {
                _creditAnalysis.postValue(it.message)
            }
        ) {
            proposalNumber = checkoutRepository.fetchProposal(
                CheckoutProposalBookletRequest(
                    entryValue = entryValue,
                    installmentQuantity = installmentQuantity
                )
            ).proposalCode
            withContext(backgroundDispatcher) {
                delay(INTERVAL_BETWEEN_ANALYSIS_CHECK)
            }
            fetchAnalysis()
        }
    }

    private fun fetchAnalysis() {
        launch(
            enableLoading = false,
            errorBlock = {
                _creditAnalysis.postValue(it.message)
            }
        ) {
            val respAnalysis = checkoutRepository.fetchAnalysis(proposalNumber).valueAnalysis
            if (respAnalysis == AnalysisStatus.PROCESSING.code || respAnalysis == AnalysisStatus.WAITING.code) {
                withContext(backgroundDispatcher) {
                    delay(INTERVAL_BETWEEN_ANALYSIS_CHECK)
                }
                fetchAnalysis()
            } else {
                _creditAnalysis.postValue(respAnalysis)
            }
        }
    }
