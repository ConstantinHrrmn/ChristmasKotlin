class Snowflake(posX: Double, posY: Double, s: Double, ss: Double, maxX : Int, maxY : Int) {
    var x = posX
    var y = posY
    var speed = s
    var si = ss

    var mX = maxX
    var mY = maxY

    fun move(){
        this.x += speed
        this.y += speed

        if (this.x > this.mX*2){
            this.x = 0.0
        }

        if (this.y > this.mY*2){
            this.y = 0.0
        }

    }

    fun getCoordinates() : Array<Double>{
        var coordinates = arrayOf<Double>()
        coordinates += this.x
        coordinates += this.y

        return coordinates
    }
}