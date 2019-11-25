import org.openrndr.Fullscreen
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.draw.loadFont
import org.openrndr.shape.Circle
import org.openrndr.shape.Color
import org.openrndr.shape.Rectangle
import org.openrndr.shape.compound
import org.openrndr.text.Writer
import kotlin.random.Random

fun main() = application {
    configure {
        width = 600
        height = 600
        title = "Lo and behold!"
    }

    program {
        val font = loadFont("data/fonts/IBMPlexMono-Regular.ttf", 220.0)
        var multiplier = 1.0
        var speed = 0.1
        var baseScale = width * 1.0
        var spacement = 100.0

        var nbEtages = 8
        var ScaleLimit = 0.05


        var startAnimation = 3
        var startColorAnimation = 7
        var flag = false

        var colors = arrayOf<Array<ColorRGBa>>()
        var lastTimeColorChange = 0;

        extend {
            var branches_haut = nbEtages
            var branches_bas = nbEtages

            drawer.background(ColorRGBa.BLACK)
            drawer.fontMap = font

            if (seconds > startAnimation && multiplier > ScaleLimit) {
                multiplier = 1.0 - (seconds - startAnimation) * speed
            }

            // -- translate to center of screen
            drawer.translate(width / 2.0, height / 2.0)

            // -- scale around origin
            drawer.scale(multiplier)
            drawer.fill = ColorRGBa.GREEN

            val writer = Writer(drawer)

            // BRANCHES
            for (etages in 0..nbEtages) {

                var x = 0.0

                if (etages % 2 != 0) {  // impair
                    x = -baseScale / 2 - (kotlin.math.floor(branches_haut / 2.0) * (baseScale + spacement))
                } else { //pair
                    x =
                        -baseScale / 2 - (kotlin.math.floor(branches_haut / 2.0) * (baseScale + spacement) - (baseScale + spacement) / 2)
                }

                for (i in 1..branches_haut) {


                    if (seconds > startColorAnimation){
                        drawer.fill = CreateColor()
                    } else{
                        drawer.fill = ColorRGBa.GREEN
                    }

                    writer.apply {
                        writer.box = Rectangle(
                            x,
                            -baseScale / 4 - etages * baseScale - baseScale,
                            baseScale,
                            baseScale / 2
                        )
                        text("CFPT-I")
                    }

                    if (i > 0) {
                        x += (baseScale + spacement)
                    }

                }

                x = 0.0

                if (etages % 2 != 0) {  // impair
                    x = -baseScale / 2 - (kotlin.math.floor(branches_bas / 2.0) * (baseScale + spacement))
                } else { //pair
                    x =
                        -baseScale / 2 - (kotlin.math.floor(branches_bas / 2.0) * (baseScale + spacement) - (baseScale + spacement) / 2)
                }

                for (i in 0..branches_bas) {

                    if (seconds > startColorAnimation){
                        drawer.fill = CreateColor()
                    } else{
                        drawer.fill = ColorRGBa.GREEN
                    }

                    writer.apply {
                        writer.box = Rectangle(
                            x,
                            -baseScale / 4 + etages * baseScale - baseScale,
                            baseScale,
                            baseScale / 2
                        )
                        text("CFPT-I")
                    }
                    if (i > 0) {
                        x += (baseScale + spacement)
                    }
                }

                branches_haut -= 1
                branches_bas += 1
            }

            // TRONC
            drawer.fill = ColorRGBa(0.71, 0.40, 0.11)
            for (th in 0..3) {
                var ytronc = -baseScale / 4 + (nbEtages * baseScale) + (th * baseScale/2) - baseScale/2

                    writer.apply {
                        writer.box = Rectangle(
                            -baseScale / 2,
                            ytronc,
                            baseScale,
                            baseScale / 2
                        )
                        text("CFPT-I")
                    }
                }

            var starRadius = 3000.0
            var starY = (nbEtages * (baseScale + spacement)) - spacement
            var starspacement = 200

            // ETOILE
            drawer.fill = ColorRGBa.YELLOW
            val cross = compound {
                union {
                    intersection {
                        shape(Circle(0.0 - (starRadius-starspacement), -starY, starRadius).shape)
                        shape(Circle(0.0 + (starRadius-starspacement), -starY, starRadius).shape)
                    }
                    intersection {
                        shape(Circle(0.0, -starY - (starRadius-starspacement), starRadius).shape)
                        shape(Circle(0.0, -starY + (starRadius-starspacement), starRadius).shape)
                    }
                }
            }
            drawer.shapes(cross)

            }
        }
    }

fun CreateColor(): ColorRGBa {
    var rnd = Random.nextInt(0, 100);
    var color = ColorRGBa.GREEN
    if (rnd % 3 == 0) {
        color = ColorRGBa(
            Random.nextDouble(0.0, 1.0),
            Random.nextDouble(0.0, 1.0),
            Random.nextDouble(0.0, 1.0)
        )
    }
    return color
}