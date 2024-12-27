package com.example.dz16bottomnavigationview.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.dz16bottomnavigationview.R
import com.example.dz16bottomnavigationview.databinding.FragmentDashboardBinding
import com.squareup.picasso.Picasso

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var viewModel: DashboardViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(requireContext())
        )[DashboardViewModel::class.java]

        observeViewModel()
        viewModel?.getCurrentWeatherByLocation()

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.updateBtn.setOnClickListener {
            viewModel?.getCurrentWeatherByCity(binding.cityEt.text.toString().trim())
        }

        return root
    }

    private fun observeViewModel() {
        viewModel?.city?.observe(viewLifecycleOwner) {
            binding.cityTv.text = it
        }
        viewModel?.temp?.observe(viewLifecycleOwner) {
            binding.tempTv.text = "$it ${getString(R.string.units_deg_c)}"
        }
        viewModel?.pressure?.observe(viewLifecycleOwner) {
            binding.pressureTv.text = "$it ${getString(R.string.units_pressure)}"
        }
        viewModel?.humidity?.observe(viewLifecycleOwner) {
            binding.humidityTv.text = "${getString(R.string.tv_humidity)} $it %"
        }
        viewModel?.windDeg?.observe(viewLifecycleOwner) {
            binding.windDegTv.text = "${getString(R.string.tv_wind_deg)} $it"
        }
        viewModel?.windSpeed?.observe(viewLifecycleOwner) {
            binding.windSpeedTv.text = "$it ${getString(R.string.units_wind_speed)}"
        }
        viewModel?.iconUrl?.observe(viewLifecycleOwner) {
            Picasso.get().load(it).into(binding.iconIv)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}