if [[ $# -lt 1 ]]; then
	echo "ERROR: Must specify target file name including png"
	exit 1
fi
newName=$1
file=$(ls -1t Sel*.png | head -1)

if [[ -f $file ]]; then
	mv $file $newName
	ls -l $newName
else
	echo "ERROR: $file not found"
fi
