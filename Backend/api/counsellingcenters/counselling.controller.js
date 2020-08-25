const { getAllCounsellingCenters } = require("./counselling.service")


module.exports = {
    getNearestCounselling: (req, res) => {
      const body = req.body;
      getAllCounsellingCenters(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            success: 0,
            message: "Database Error"
          });
        }
        return res.status(200).json({
          success: 1,
          data: calculateNearestCounselling(body,results)
        });
      });
    }
};
function calculateDistance(lat1, lon1, lat2, lon2) {
    var p = 0.017453292519943295;    // Math.PI / 180
    var c = Math.cos;
    var a = 0.5 - c((lat2 - lat1) * p)/2 + 
            c(lat1 * p) * c(lat2 * p) * 
            (1 - c((lon2 - lon1) * p))/2;
  
    return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
  }
function calculateNearestCounselling(body,results){
    nearestCentre = {};
    count = 0;
    myResults = results;
    i = 0;
    for(val of results){
        distance = calculateDistance(val.latitude,val.longitude,body.latitude,body.longitude);
        myResults[i].calculatedDistance = distance;
        i++;
    }
    myResults.sort((dis1,dis2) => (dis1.calculatedDistance>dis2.calculatedDistance) ? 1:-1 );
    return myResults.slice(0,8);
}