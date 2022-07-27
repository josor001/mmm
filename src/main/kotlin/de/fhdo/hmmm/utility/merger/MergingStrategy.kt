package de.fhdo.hmmm.utility.merger

import de.fhdo.hmmm.utility.model.Microservice
import de.fhdo.hmmm.utility.model.System

interface MergingStrategy {
   fun merge(foundation : System, addition : System)
}

object byServiceNameStrategy : MergingStrategy { // first strategy
    override fun merge(foundation: System, addition: System) {
        //Important: equals as well as hashcode methods of [Microservice] already only use [name] by default
        //adds all microservices and contracts of addition to foundation
        foundation.microservices.addAll(addition.microservices)
        foundation.contracts.addAll(addition.contracts)
    }
}

object byNameAndTeamNameStrategy : MergingStrategy { // first strategy
    override fun merge(foundation: System, addition: System) { // concrete algorithm implementation
        //Important: equals and hashcode methods of [Microservice] are overridden in the following!
        //adds all microservices and contracts of addition to foundation
        TODO("WRITE TEST IF OVERRIDING LIKE THIS WORKS!")
        foundation.microservices.addAll(addition.microservices)
        foundation.contracts.addAll(addition.contracts)
    }

    /**
     * Extension method of [Microservice] changing the equals method to also include its owning team.
     * Due to scope only applicable in this byNameAndTeamStrategy object.
     */
    fun Microservice.equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Microservice

        if (name != other.name) return false
        if (owner != other.owner) return false

        return true
    }

    /**
     * Extension method of [Microservice] changing the hashCode method to also include its owning team.
     * Due to scope only applicable in this byNameAndTeamStrategy object.
     */
    fun Microservice.hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (owner?.hashCode() ?: 0)
        return result
    }

}

object byServiceAndInterfacesNameStrategy : MergingStrategy { // first strategy
    override fun merge(foundation: System, addition: System) { // concrete algorithm implementation
        TODO("NOT IMPLEMENTED YET")
    }
}