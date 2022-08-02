package de.fhdo.hmmm.utility.merger

import de.fhdo.hmmm.utility.model.SystemFragment

class ModelMerger(private val strategy: MergingStrategy) {
    fun merge(foundation : SystemFragment, addition : SystemFragment) : SystemFragment {
        return strategy.merge(foundation, addition)
    }
}