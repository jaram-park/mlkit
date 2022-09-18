package com.google.mlkit.vision.demo.kotlin.posedetector

import com.google.mlkit.vision.pose.PoseLandmark
import kotlin.math.atan2

class Joint (var firstPoint: PoseLandmark, var midPoint: PoseLandmark, var lastPoint: PoseLandmark,
            var minAngleRange: Int, var maxAngleRange: Int, var poseMidPoint: String){

    fun getFeedback(): String{
        var currentAngle = getAngle()
        var poseFeedback: String
        var poseStatus = if (currentAngle > maxAngleRange){
            "조금만 더 내려요! "
        }else if (currentAngle >= minAngleRange && currentAngle >= maxAngleRange){
            "적절한 각도 입니다. \n 3초 정도 유지해주세요. 1, 2, 3"
        }else if (currentAngle < minAngleRange){
            "무리가요."
        }else{
            "좋습니다!"
        }
        poseFeedback = "$poseMidPoint\n $poseStatus"
        return poseFeedback
    }


    fun getAngle(): Double {
        var result = Math.toDegrees(
            (atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                lastPoint.getPosition().x - midPoint.getPosition().x)
                    - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                firstPoint.getPosition().x - midPoint.getPosition().x)).toDouble()
        )
        result = Math.abs(result) // Angle should never be negative
        if (result > 180) {
            result = 360.0 - result // Always get the acute representation of the angle
        }
        return result
    }

}