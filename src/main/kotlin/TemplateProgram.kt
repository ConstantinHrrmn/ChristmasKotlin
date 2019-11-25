import org.openrndr.Fullscreen
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.draw.loadFont
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer
import kotlin.random.Random

fun main() = application {
    configure {

        // Garde la résolution mise ci-dessous dans le fullscreen mode
        width = 600
        height = 600
        //fullscreen = Fullscreen.SET_DISPLAY_MODE

        // Met l'application en fullscreen mais en gardant la résolution de l'écran
        //fullscreen = Fullscreen.CURRENT_DISPLAY_MODE

        // Titre de la page
        title = "Lo and behold!"
    }

    program {

        // Charger une image
        //val image = loadImage("data/images/pm5544.png")

        // Charger une font pour pouvoir écrire
        val font = loadFont("data/fonts/IBMPlexMono-Regular.ttf", 220.0)
        var multiplier = 1.0
        var speed = 0.1
        var baseScale = width * 1.0
        var spacement = 100.0

        var nbEtages = 8
        var ScaleLimit = 0.05


        var startAnimation = 3
        var startColorAnimation = 7

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
                        var rnd = Random.nextInt(0, 100);

                        if (rnd % 4 == 0) {
                            drawer.fill = ColorRGBa(
                                Random.nextDouble(0.0, 1.0),
                                Random.nextDouble(0.0, 1.0),
                                Random.nextDouble(0.0, 1.0)
                            )
                        } else {
                            drawer.fill = ColorRGBa.GREEN
                        }
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
                        var rnd = Random.nextInt(0, 100);

                        if (rnd % 4 == 0) {
                            drawer.fill = ColorRGBa(
                                Random.nextDouble(0.0, 1.0),
                                Random.nextDouble(0.0, 1.0),
                                Random.nextDouble(0.0, 1.0)
                            )
                        } else {
                            drawer.fill = ColorRGBa.GREEN
                        }
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
            }
        }
    }
