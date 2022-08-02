package de.fhdo.hmmm.utility.merger

import de.fhdo.hmmm.utility.model.SystemFragment

interface MergingStrategy {
   fun merge(base : SystemFragment, addition : SystemFragment) : SystemFragment
}

object ByNameOfServiceAndTeamStrategy : MergingStrategy { // first strategy
    override fun merge(base: SystemFragment, addition: SystemFragment): SystemFragment {
        //Important: equals as well as hashcode methods of [Microservice] already only use [name] by default
        //adds all microservices and contracts of addition to foundation
        base.microservices.addAll(addition.microservices)
        base.contracts.addAll(addition.contracts)
        return base
    }
}

// Careful, the runtime of this strategy seems to be O(n^2)
object ByNameOfServiceStrategy : MergingStrategy {
    override fun merge(base: SystemFragment, addition: SystemFragment): SystemFragment {
        addition.microservices.forEach { newService ->
            var found : Boolean = false
            base.microservices.forEach { existingService ->
                if(newService.name == existingService.name) {
                    found = true
                }
            }
            if(!found)
                base.microservices.add(newService)
        }

        addition.contracts.forEach { newContract ->
            var found : Boolean = false
            base.contracts.forEach { existingContract ->
                if(newContract.consumer.name == existingContract.consumer.name &&
                        newContract.owner.name == existingContract.owner.name) {
                    found = true
                }
            }
            if(!found)
                base.contracts.add(newContract)
        }
        return base
    }
}