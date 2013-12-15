package nl.jappieklooster.kook.book

import grails.test.mixin.TestFor
import spock.lang.Specification
import nl.jappieklooster.kook.quantification.*
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Ingredient)
@Mock([Content, Unit, Dimension])
class IngredientSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test if the toString returns the correct format"() {
		when: "The ingredient is created with a proper unit"
			def instance = new Ingredient(
				prepend:  null,
				ammend:  null,
				data: new Content(
					name: "aardbij",
					unit: new Unit(
							name:"kilo",
							plural:"kilo's",
							dimension:new Dimension(
								name:"gewicht"
							)
						)
					)
				)
		then: "the tostring should be capitalized only with the first word"
			instance.toString() == "Aardbij"

		when: "The ingredient contains a prepend and ammend"
			instance.prepend = "diepvries"
			instance.ammend = "gesneden"
		then: "The tostring formats them on the correct positions and capitalizes the prepend"
			instance.toString() == "Diepvries aardbij gesneden"
    }
}
