import de.fhdo.hmmm.utility.merger.ByNameOfServiceAndTeamStrategy
import de.fhdo.hmmm.utility.merger.ModelMerger
import de.fhdo.hmmm.utility.model.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory


/**
 * Tests for merging [SystemFragment]s.
 *
 * This test class sets up three small test systems each comprising 2-3 microservices and tests
 * merging the fragments with the [ModelMerger].
 *
 * @author Jonas Sorgalla
 */
internal class MergerTest {

    val logger = LoggerFactory.getLogger(MergerTest::class.java)

    // Build example system
    val systemA = SystemFragment("System A")
    val systemB = SystemFragment("System B")
    val systemC = SystemFragment("System C")
    val teamA = Team("Team A")
    val teamB = Team("Team B")
    val teamC = Team("Team C")
    val serviceA1 = Microservice("Microservice A1", teamA)
    val interfaceA1 = Interface("Interface A1", serviceA1)
    val serviceA2 = Microservice("Microservice A2", teamA)
    val serviceB1 = Microservice("Microservice B", teamB)
    val serviceB2 = Microservice("Microservice B", teamB)
    val serviceC = Microservice("Microservice C", teamC)

    @BeforeEach
    internal fun setUp() {
        systemA.microservices.add(serviceA1)
        systemA.microservices.add(serviceA2)
        systemA.contracts.add(Contract(interfaceA1, serviceA2))
        systemA.microservices.add(serviceB1)

        systemB.microservices.add(serviceB2)
        systemB.microservices.add(serviceC)
    }

    @Test
    fun areServicesTheSame() {
        assertTrue(serviceB1 == serviceB2)
    }
    @Test
    fun mergeTwoSystemsUsingDefaultStrategy() {
        val merger = ModelMerger(ByNameOfServiceAndTeamStrategy)
        val systemAB = merger.merge(systemA, systemB)
        logger.info("System A")
        systemA.microservices.forEach {
            logger.info(it.toString())
        }
        logger.info("System B")
        systemB.microservices.forEach {
            logger.info(it.toString())
        }
        logger.info("Merged System AB")
        systemAB.microservices.forEach {
            logger.info(it.toString())
        }
        // serviceA1, serviceA2, serviceB1, serviceC - serviceB2 should not be included
        // because it is a duplicate of serviceB1
        assertTrue(systemAB.microservices.size == 4)
    }
}