package nl.jappieklooster.kook.book

import grails.test.mixin.TestFor
import spock.lang.Specification
import nl.jappieklooster.kook.quantification.*
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Content)
@Mock([Unit, Dimension])
class ContentSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "test if the toString returns the correct format"() {
		when: "The content is created with a proper unit"
			def instance = new Content(
					name: "aardbij",
					unit: new Unit(
							name:"kilo",
							plural:"kilo's",
							dimension:new Dimension(
								name:"gewicht"
							)
						)
					)
		then: "the tostring should be capitalized only with the first word"
			instance.toString() == "Aardbij gewicht: kilo's"
    }
}
