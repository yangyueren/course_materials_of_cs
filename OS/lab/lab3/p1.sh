cd /Linux-myext2/part1
chmod 777 copy.sh
chmod 777 substitute.sh
./copy.sh
./substitute.sh


cp myext2_fs.h /lib/modules/$(uname -r)/build /include/linux/
cp myext2-atomic.h /lib/modules/$(uname -r)/build /include/asm-generic/bitops/
cp myext2-atomic-setbit.h /lib/modules/$(uname -r)/build /include/asm-generic/bitops/
cp bitops_asm_generic.h /lib/modules/$(uname -r)/build /include/asm-generic/bitops.h
cp bitops_asm.h /lib/modules/$(uname -r)/build /arch/x86/include/asm/bitops.h
cp magic.h /lib/modules/$(uname -r)/build /include/uapi/linux/magic.h 



