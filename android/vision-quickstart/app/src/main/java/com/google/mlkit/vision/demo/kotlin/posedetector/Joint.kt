package com.google.mlkit.vision.demo.kotlin.posedetector

import com.google.mlkit.vision.pose.PoseLandmark
import kotlin.math.atan2

class Joint (var firstPoint: PoseLandmark, var midPoint: PoseLandmark, var lastPoint: PoseLandmark,
            var targetMinAngleRange: Int, var targetMaxAngleRange: Int,
             var basicMinAngleRange: Int, var basicMaxAngleRange: Int, var poseMidPoint: String){


    fun getFeedback(): Pair<String, String>{
        var currentAngle = getAngle()
        var poseFeedback: String

        var actionStatus = if (currentAngle >= targetMinAngleRange && currentAngle <= targetMaxAngleRange){
            "target_posture"
        }else if (currentAngle >=basicMinAngleRange && currentAngle <=basicMaxAngleRange){
            "basic_posture"
        }else {
            "in_doing"
        }


        var poseStatus = if (currentAngle > targetMaxAngleRange){
            "조금 더 아래로! "
        }else if (currentAngle >= targetMinAngleRange && currentAngle <= targetMaxAngleRange){
            "지금 좋습니다. \n"
        }else if (currentAngle < targetMinAngleRange){
            "조금 더 위로!"
        }else{
            "좋습니다!"
        }
        poseFeedback = "$currentAngle $poseMidPoint\n $poseStatus"
        return Pair(poseFeedback, actionStatus)
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