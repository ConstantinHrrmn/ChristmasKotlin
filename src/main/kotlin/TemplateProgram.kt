import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.draw.loadFont
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import org.openrndr.shape.compound
import org.openrndr.text.Writer
import kotlin.math.sqrt
import kotlin.random.Random

var snowflakes = arrayOf<Snowflake>()
var snowflakesAmount = 300

var text = "CFPT-I"

fun main() = application {
    configure {
        width = 600
        height = 600
        title = "HO HO HO !"
    }

    program {
        val font = loadFont("data/fonts/IBMPlexMono-Regular.ttf", 220.0)

        var multiplier = 1.0
        var speed = 0.1
        var baseScale = width * 1.0
        var spacement = 100.0
        var nbEtages = 8
        var ScaleLimit = 0.05

        // ANIMATIONS
        var startAnimation = 5
        var startColorAnimation = 8
        var startSnowAnimation = 2

        // VARIABLES ETOILES
        var nbStarBranch = 6;
        var startangle = 0
        var spacementAngle = 360 / nbStarBranch

        // For the colors
        var colors = arrayOf<Array<ColorRGBa>>()
        for (i in 0..nbEtages * 2) {
            var array = arrayOf<ColorRGBa>()
            for (x in 0..nbEtages * 2)
                array += CreateColor()
            colors += array
        }
        var flag = false
        var lastTimeColorChange = 0;

        //createSnowflakes(width/2, height/2, snowflakesAmount)

        extend {
            var branchesHaut = nbEtages
            var branchesBas = nbEtages

            drawer.isolated {
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

                if (seconds.toInt() > lastTimeColorChange && seconds.toInt() > startColorAnimation) {
                    flag = true
                    lastTimeColorChange = seconds.toInt()
                }

                // BRANCHES
                for (etages in 0..nbEtages) {

                    var x = 0.0

                    if (etages % 2 != 0) {  // impair
                        x = -baseScale / 2 - (kotlin.math.floor(branchesHaut / 2.0) * (baseScale + spacement))
                    } else { //pair
                        x =
                            -baseScale / 2 - (kotlin.math.floor(branchesHaut / 2.0) * (baseScale + spacement) - (baseScale + spacement) / 2)
                    }

                    for (i in 1..branchesHaut) {

                        if (flag)
                            colors[etages][i] = CreateColor()

                        drawer.fill = colors[etages][i]

                        writer.apply {
                            writer.box = Rectangle(
                                x,
                                -baseScale / 4 - etages * baseScale - baseScale,
                                baseScale,
                                baseScale / 2
                            )
                            text(text)
                        }

                        if (i > 0) {
                            x += (baseScale + spacement)
                        }

                    }

                    x = 0.0
                    x = if (etages % 2 != 0) {  // impair
                        -baseScale / 2 - (kotlin.math.floor(branchesBas / 2.0) * (baseScale + spacement))
                    } else { //pair
                        -baseScale / 2 - (kotlin.math.floor(branchesBas / 2.0) * (baseScale + spacement) - (baseScale + spacement) / 2)
                    }

                    for (i in 0..branchesBas) {

                        if (flag)
                            colors[etages][i] = CreateColor()

                        drawer.fill = colors[etages][i]

                        writer.apply {
                            writer.box = Rectangle(
                                x,
                                -baseScale / 4 + etages * baseScale - baseScale,
                                baseScale,
                                baseScale / 2
                            )
                            text(text)
                        }
                        if (i > 0) {
                            x += (baseScale + spacement)
                        }
                    }

                    branchesHaut -= 1
                    branchesBas += 1
                }

                flag = false

                // TRONC
                drawer.fill = ColorRGBa(0.71, 0.40, 0.11)
                for (th in 0..3) {
                    var ytronc = -baseScale / 4 + (nbEtages * baseScale) + (th * baseScale / 2) - baseScale / 2

                    writer.apply {
                        writer.box = Rectangle(
                            -baseScale / 2,
                            ytronc,
                            baseScale,
                            baseScale / 2
                        )
                        text(text)
                    }
                }

                //ETOILE
                drawer.fill = ColorRGBa.YELLOW



                    for(b in 0 until nbStarBranch) {
                        drawer.isolated {
                            drawer.translate(0.0, -nbEtages * (baseScale + spacement) + spacement * 2)
                            drawer.rotate(startangle + (b * spacementAngle * 1.0) )
                            writer.apply {
                                writer.box = Rectangle(
                                    0.0,
                                    0.0 - baseScale / 4,
                                    baseScale,
                                    baseScale / 2
                                )
                                text(text)
                            }
                        }
                    }
            }

            if(seconds > startSnowAnimation){
                drawer.isolated {
                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.strokeWeight = 1.0

                    for (flake in snowflakes){
                        flake.move()
                        drawer.circle(flake.x.toDouble(), flake.y.toDouble(), flake.si)
                    }
                }
                if(snowflakes.size < snowflakesAmount)
                    createSnowflakes(width, height, seconds.toInt() * seconds.toInt())
            }
        }
    }
}

fun CreateColor(): ColorRGBa {
    var rnd = Random.nextInt(0, 3);
    var color = ColorRGBa.GREEN
    if (rnd == 0) {
        color = ColorRGBa(
            Random.nextDouble(0.0, 1.0),
            Random.nextDouble(0.0, 1.0),
            Random.nextDouble(0.0, 1.0)
        )
    }
    return color
}

fun createSnowflakes(maxX : Int, maxY : Int, amount: Int){

    for (i in 0..amount){
        var x = Random.nextInt(-(maxX), maxX)
        var y = Random.nextInt(-(maxY), maxY)
        var speed = Random.nextDouble(3.0, 10.0)
        var size = Random.nextDouble(1.0, 3.5)
        var snowflake = Snowflake(x.toDouble(),y.toDouble(),speed, size, maxX, maxY)

        snowflakes += snowflake
    }
}