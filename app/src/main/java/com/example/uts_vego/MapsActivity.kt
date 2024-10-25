import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_vego.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity() {
    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Set konfigurasi OSM
        Configuration.getInstance().userAgentValue = packageName

        // Inisialisasi MapView
        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)

        // Atur posisi dan zoom peta
        val mapController = map.controller
        mapController.setZoom(15.0)
        mapController.setCenter(GeoPoint(-6.2088, 106.8456)) // Jakarta sebagai contoh

        // Tambahkan marker
        val startPoint = GeoPoint(-6.2088, 106.8456)
        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        startMarker.title = "Posisi Saya"
        map.overlays.add(startMarker)

        // Jika menggunakan savedInstanceState
        map.onCreate(savedInstanceState)
    }

    // Pastikan untuk memanggil metode siklus hidup MapView
    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDetach()
    }
}
