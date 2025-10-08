#!/bin/bash

# List of fully qualified benchmark names
benchmarks=(
  "org.example.benchmarks.UtenteBenchmark.benchGetName"
  "org.example.benchmarks.UtenteBenchmark.benchGetSurname"
  "org.example.benchmarks.UtenteBenchmark.benchGetTelephone"
  "org.example.benchmarks.UtenteBenchmark.benchGetAddress"
  "org.example.benchmarks.UtenteBenchmark.benchGetContoBancario"
  "org.example.benchmarks.UtenteBenchmark.benchSetName"
  "org.example.benchmarks.UtenteBenchmark.benchSetSurname"
  "org.example.benchmarks.UtenteBenchmark.benchSetConto"
  "org.example.benchmarks.TecnicoBenchmark.benchGetName"
  "org.example.benchmarks.TecnicoBenchmark.benchGetSurname"
  "org.example.benchmarks.TecnicoBenchmark.benchGetProfession"
  "org.example.benchmarks.TecnicoBenchmark.benchGetCode"
  "org.example.benchmarks.TecnicoBenchmark.benchSetName"
  "org.example.benchmarks.TecnicoBenchmark.benchSetSurname"
  "org.example.benchmarks.TecnicoBenchmark.benchSetProfession"
  "org.example.benchmarks.TecnicoBenchmark.benchSetCode"
  "org.example.benchmarks.ContoBancarioBenchmark.benchVersamento"
  "org.example.benchmarks.ContoBancarioBenchmark.benchPrelievo_SufficienteSaldo"
  "org.example.benchmarks.ContoBancarioBenchmark.benchPrelievo_InsufficienteSaldo"
  "org.example.benchmarks.ContoBancarioBenchmark.benchgetId"
  "org.example.benchmarks.ContoBancarioBenchmark.benchSetId"
  "org.example.benchmarks.ContoBancarioBenchmark.benchGetSaldo"
  "org.example.benchmarks.ContoBancarioBenchmark.benchSetSaldo"
  "org.example.benchmarks.ContoBancarioBenchmark.benchSetSaldo2"
  "org.example.benchmarks.AmministratoreBench.benchGetName"
  "org.example.benchmarks.AmministratoreBench.benchGetSurname"
  "org.example.benchmarks.AmministratoreBench.benchGetDepartment"
  "org.example.benchmarks.AmministratoreBench.benchSetName"
  "org.example.benchmarks.AmministratoreBench.benchSetSurname"
  "org.example.benchmarks.AmministratoreBench.benchSetDepartment"
)

# Path to JMH benchmarks
JAR_PATH="target/MavenProjectJ4-1.0-SNAPSHOT.jar"
COMMON_ARGS="-f 10 -i 3000 -wi 0 -bm ss -tu ms"

#prepare_system() {
  #echo ">>> Disabling Turbo Boost"
  #echo 1 | sudo /usr/bin/tee /sys/devices/system/cpu/intel_pstate/no_turbo
  #echo ">>> Disabling Hyper-Threading"
  #for cpu in {0..7}; do echo 0 | sudo /usr/bin/tee /sys/devices/system/cpu/cpu$cpu/online; done
  #echo ">>> Disabling ASLR"
  #echo 0 | sudo /usr/bin/tee /proc/sys/kernel/randomize_va_space
  #echo ">>> Stopping non-essential services"
  #sudo /usr/bin/systemctl stop bluetooth.service
  #sudo /usr/bin/systemctl stop cups.service
  #sudo /usr/bin/systemctl stop cups-browsed.service
  #sudo /usr/bin/systemctl stop fwupd.service
  #sudo /usr/bin/systemctl stop ModemManager.service
  #sudo /usr/bin/systemctl stop NetworkManager.service
  #sudo /usr/bin/systemctl stop wpa_supplicant.service
  #sudo /usr/bin/systemctl stop upower.service
  #sudo /usr/bin/systemctl stop switcheroo-control.service
#}

#restore_system() {
  #echo ">>> Re-enabling Hyper-Threading"
  #for cpu in {0..7}; do echo 1 | sudo /usr/bin/tee /sys/devices/system/cpu/cpu$cpu/online; done
  #echo ">>> Re-enabling Turbo Boost"
  #echo 0 | sudo /usr/bin/tee /sys/devices/system/cpu/intel_pstate/no_turbo
  #echo ">>> Re-enabling ASLR"
  #echo 2 | sudo /usr/bin/tee /proc/sys/kernel/randomize_va_space
  #echo ">>> Restarting services"
  #sudo /usr/bin/systemctl start bluetooth.service
  #sudo /usr/bin/systemctl start cups.service
  #sudo /usr/bin/systemctl start cups-browsed.service
  #sudo /usr/bin/systemctl start fwupd.service
  #sudo /usr/bin/systemctl start ModemManager.service
  #sudo /usr/bin/systemctl start NetworkManager.service
  #sudo /usr/bin/systemctl start wpa_supplicant.service
  #sudo /usr/bin/systemctl start upower.service
  #sudo /usr/bin/systemctl start switcheroo-control.service
#}

# Random execution loop
remaining_benchmarks=("${benchmarks[@]}")
total=${#remaining_benchmarks[@]}

# Main loop
for ((i = 0; i < total; i++)); do
  idx=$((RANDOM % ${#remaining_benchmarks[@]}))
  bm="${remaining_benchmarks[$idx]}"
  unset 'remaining_benchmarks[idx]'
  # Re-index array to avoid holes
  remaining_benchmarks=("${remaining_benchmarks[@]}")

  echo "### Executing benchmark: $bm"

  #class_name=$(echo "$bm" | awk -F'.' '{split($(NF-2), a, "_"); print a[1]}')
  #method_name=$(echo "$bm" | awk -F'.' '{print $NF}')
  class_name=$(echo "$bm" | awk -F'.' '{$NF=""; sub(/\.$/, ""); print $0}' OFS='.')
  method_name=$(echo "$bm" | awk -F'.' '{print $NF}')

  #output_dir="$class_name/$method_name"
  mkdir -p "$class_name"

  #echo "  -> Execution $i: preparing the system"
  #prepare_system
  echo "  -> Execution $i: running benchmark"
  nice -n -20 java -Xms8G -Xmx8G -jar "$JAR_PATH" "$bm"  $COMMON_ARGS -rff "$class_name/$method_name-benchmark-results.json" -rf json
done

#echo ">>> All benchmarks executed, restoring system"
echo ">>> All benchmarks executed"
#restore_system
echo ">>> Finished."
