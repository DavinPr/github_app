package com.app.githubmobile.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.app.githubmobile.R
import com.app.githubmobile.databinding.SettingsActivityBinding
import com.app.githubmobile.helper.setLocale
import com.app.githubmobile.service.AlarmReceiver
import com.app.githubmobile.ui.home.dashboard.DashboardFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(this@SettingsActivity, R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private lateinit var language: String
        private lateinit var nightMode: String
        private lateinit var dailyReminder: String
        private lateinit var latestLang: String

        private lateinit var dailyReminderSwitch: SwitchPreference
        private lateinit var alarmReceiver: AlarmReceiver

        companion object {
            private const val DEFAULT_LANG = "en"
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            init()

        }

        private fun init() {
            language = resources.getString(R.string.language_key)
            nightMode = resources.getString(R.string.night_mode_key)
            dailyReminder = resources.getString(R.string.daily_reminder_key)
            latestLang =
                preferenceManager.sharedPreferences.getString(language, DEFAULT_LANG).toString()

            dailyReminderSwitch =
                findPreference<SwitchPreference>(dailyReminder) as SwitchPreference

            alarmReceiver = AlarmReceiver()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        @ExperimentalCoroutinesApi
        @FlowPreview
        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            if (key == language) {
                setLocale(
                    requireActivity(),
                    sharedPreferences?.getString(language, DEFAULT_LANG) ?: DEFAULT_LANG
                )
                if (latestLang != sharedPreferences?.getString(language, DEFAULT_LANG)) {
                    val returnIntent = Intent()
                    activity?.setResult(DashboardFragment.SETTING_RESULT, returnIntent)
                }
                preferenceScreen = null
                addPreferencesFromResource(R.xml.root_preferences)
            }

            if (key == dailyReminder) {
                if (dailyReminderSwitch.isChecked) {
                    alarmReceiver.setReminder(requireContext(), "09:00")
                } else {
                    alarmReceiver.cancelReminder(requireContext())
                }
            }
        }
    }

}