package com.example.weather.screens.cityList

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.FragmentLocalOrCityBinding
import com.example.weather.repository.Repositories
import com.example.weather.repository.city.room.entities.City
import com.example.weather.utils.viewModelCreator
import com.google.android.gms.location.LocationServices

class CityListFragment : Fragment(R.layout.fragment_local_or_city){

    private lateinit var bindingLocalOrCity: FragmentLocalOrCityBinding
    private lateinit var currentCity: City
    private lateinit var adapter: CityAdapter

    private val viewModel by viewModelCreator{CityListViewModel(Repositories.cityRepository)}

    private val requestLocationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(),   // contract for requesting more than 1 permission
        ::onGotLocationPermissionsResult
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingLocalOrCity = FragmentLocalOrCityBinding.bind(view)
        bindingLocalOrCity.saveProgressGroup.visibility = View.GONE
        viewModel.cities.observe(viewLifecycleOwner){
            adapter.cities = it
        }

        adapter = CityAdapter(object : CityActionListener {
            override fun onCityMove(city: City, moveBy: Int) {
                viewModel.move(city, moveBy)
            }

            override fun details(city: City) {
                weatherInCityFragment(city)
            }

            override fun deleteCity(city: City) {
                viewModel.deleteCity(city)
            }
        })
        bindingLocalOrCity.rcItem.layoutManager = LinearLayoutManager(requireContext())
        bindingLocalOrCity.rcItem.adapter = adapter
        bindingLocalOrCity.local.setOnClickListener{
            if (statusInternet()) {
                requestLocationPermissionsLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }
            else Toast.makeText(requireActivity(), "Проверьте состояние инернета", Toast.LENGTH_LONG).show()
        }
        bindingLocalOrCity.cancelAction.setOnClickListener{
            viewModel.cancel()
        }
        observeState()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        if (it.InProgress == 0){
            bindingLocalOrCity.rcItem.visibility = View.VISIBLE
            bindingLocalOrCity.local.visibility = View.VISIBLE
            bindingLocalOrCity.saveProgressGroup.visibility = View.GONE
        }
        else{
            bindingLocalOrCity.root.children.forEach { elements ->
                elements.visibility = View.GONE }
            bindingLocalOrCity.saveProgressGroup.visibility = View.VISIBLE
            bindingLocalOrCity.saveProgressBar.progress = it.InProgress
            bindingLocalOrCity.savingPercentageTextView.text = resources.getString(R.string.percentage_value, it.InProgress)
        }
    }

    private fun weatherInCityFragment(city: City) {
        val direction = CityListFragmentDirections.actionCityListFragmentToWeatherInCityFragment(city)
        findNavController().navigate(direction)
    }

    private fun onGotLocationPermissionsResult(grantResults: Map<String, Boolean>){
        if (grantResults[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            lastLocation()
        } else {
            // example of handling 'Deny & don't ask again' user choice
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                askUserForOpeningAppSettings()
            } else {
                Toast.makeText(requireContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireActivity().packageName, null)
        )
        if (requireActivity().packageManager.resolveActivity(appSettingsIntent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            Toast.makeText(requireActivity(), R.string.permissions_denied_forever, Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(R.string.open) { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()
        }
    }

    private fun lastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { it: Location ->
                    currentCity = City(111, "", "Локальный город", location.latitude, location.longitude)
                    weatherInCityFragment(currentCity)
                }
            }
        }
    }

    private fun statusInternet(): Boolean{
        val cm = requireActivity().getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}