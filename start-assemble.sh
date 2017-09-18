#!/bin/bash -e
# A SHORT DESCRIPTION OF YOUR SCRIPT GOES HERE
###############################################################################
set -e          # exit on command errors (so you MUST handle exit codes properly!)
set -E          # pass trap handlers down to subshells
set -o pipefail # capture fail exit codes in piped commands
#set -x         # execution tracing debug messages


# Error handler
on_err() {
	echo ">> ERROR: $?"
	FN=0
	for LN in "${BASH_LINENO[@]}"; do
		[ "${FUNCNAME[$FN]}" = "main" ] && break
		echo ">> ${BASH_SOURCE[$FN]} $LN ${FUNCNAME[$FN]}"
		FN=$(( FN + 1 ))
	done
}
trap on_err ERR

# Exit handler
declare -a EXIT_CMDS
add_exit_cmd() { EXIT_CMDS+="$*;  "; }
on_exit(){ eval "${EXIT_CMDS[@]}"; }
trap on_exit EXIT

# Get command info
CMD_PWD=$(pwd)
CMD="$0"
CMD_DIR="$(cd "$(dirname "$CMD")" && pwd -P)"

# Defaults and command line options
[ "$VERBOSE" ] ||  VERBOSE=
[ "$DEBUG" ]   ||  DEBUG=
[ "$BUILD" ]   ||  BUILD="FALSE"
[ "$RUN" ]   ||  RUN="FALSE"


#>>>> PUT YOUR ENV VAR DEFAULTS HERE <<<<

# Basic helpers
out() { echo "$(date +%Y%m%dT%H%M%SZ): $*"; }
err() { out "$*" 1>&2; }
vrb() { [ ! "$VERBOSE" ] || out "$@"; }
dbg() { [ ! "$DEBUG" ] || err "$@"; }
die() { err "EXIT: $1" && [ "$2" ] && [ "$2" -ge 0 ] && exit "$2" || exit 1; }

# Show help function to be used below
show_help() {
	awk 'NR>1{print} /^(###|$)/{exit}' "$CMD"
	echo "USAGE: $(basename "$CMD") [arguments]"
	echo "ARGS:"
	MSG=$(awk '/^NARGS=-1; while/,/^esac; done/' "$CMD" | sed -e 's/^[[:space:]]*/  /' -e 's/|/, /' -e 's/)//' | grep '^  -')
	EMSG=$(eval "echo \"$MSG\"")
	echo "$EMSG"
}


build() {
	cd $CMD_DIR
	sbt clean build
}

assembly() {
	cd $CMD_DIR
	sbt clean assembly
}


start() {
    cd $CMD_DIR
    sbt ";project web;~run"
}

# Parse command line options (odd formatting to simplify show_help() above)
NARGS=-1; while [ "$#" -ne "$NARGS" ]; do NARGS=$#; case $1 in
	# SWITCHES
	-h|--help)      # This help message
		show_help; exit 1; ;;
    -b|--build)     # Build
		BUILD="TRUE"; shift; ;;
    -r|--run)     # Run
        RUN="TRUE"; shift; ;;
	*)
	    break ;;
esac; done

[ "$DEBUG" ]  &&  set -x

###############################################################################

# Validate some things
#[ $# -gt 0 -a -z "$THING" ]  &&  THING="$1"  &&  shift
#[ "$THING" ]  ||  die "You must provide some thing!"
#[ $# -eq 0 ]  ||  die "ERROR: Unexpected commands!"
if [[ "$BUILD" = "TRUE" ]]; then
        out "Building Assemble"
        build;
fi

if [[ "$RUN" = "TRUE" ]]; then
        out "Starting.."
        start;
fi