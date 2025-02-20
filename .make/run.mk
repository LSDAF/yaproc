# Run targets for yaproc application

# Variables
JAR_FILE := target/yaproc.jar
DEFAULT_INPUT := test.properties
DEFAULT_OUTPUT := output.yml

# Help targets
.PHONY: run-help
run-help:
	@echo "YAPROC Run Options"
	@echo "================="
	@echo ""
	@echo "Basic Usage:"
	@echo "  make run-yaml       - Convert to YAML (using default files)"
	@echo "  make run-json      - Convert to JSON (using default files)"
	@echo "  make run-prop      - Convert to Properties (using default files)"
	@echo ""
	@echo "Debug Options:"
	@echo "  make run-debug     - Run with debug output"
	@echo "  make run-verbose   - Run with verbose output"
	@echo ""
	@echo "Force Overwrite:"
	@echo "  make run-yaml-force - Convert to YAML and overwrite output"
	@echo "  make run-json-force - Convert to JSON and overwrite output"
	@echo "  make run-prop-force - Convert to Properties and overwrite output"
	@echo ""
	@echo "Custom Files:"
	@echo "  make run-yaml IN=input.json OUT=output.yml"
	@echo "  make run-json IN=input.yml OUT=output.json"
	@echo "  make run-prop IN=input.json OUT=output.properties"
	@echo ""
	@echo "Additional Flags:"
	@echo "  FORCE=true         - Add -f flag to overwrite existing files"
	@echo "  DEBUG=true         - Add --debug flag for detailed debug info"
	@echo "  VERBOSE=true       - Add --verbose flag for basic progress info"
	@echo ""
	@echo "Examples:"
	@echo "  make run-yaml IN=config.json OUT=config.yml FORCE=true    # Convert and overwrite"
	@echo "  make run-yaml DEBUG=true                                 # Convert with debug output"
	@echo "  make run-json IN=settings.yml OUT=settings.json VERBOSE=true  # Convert with progress info"

# Build flags based on variables
FLAGS := $(if $(filter true,$(FORCE)),-f,)
FLAGS += $(if $(filter true,$(DEBUG)),--debug,)
FLAGS += $(if $(filter true,$(VERBOSE)),--verbose,)

# Input/Output file handling
IN ?= $(DEFAULT_INPUT)
OUT ?= $(DEFAULT_OUTPUT)

# Basic run targets
.PHONY: run-yaml run-json run-prop
run-yaml: install
	@java -jar $(JAR_FILE) yaml $(FLAGS) $(IN) $(OUT)

run-json: install
	@java -jar $(JAR_FILE) json $(FLAGS) $(IN) $(OUT)

run-prop: install
	@java -jar $(JAR_FILE) properties $(FLAGS) $(IN) $(OUT)

# Debug and verbose targets
.PHONY: run-debug run-verbose
run-debug: DEBUG=true
run-debug: run-yaml

run-verbose: VERBOSE=true
run-verbose: run-yaml

# Example targets with common combinations
.PHONY: run-yaml-force run-json-force run-prop-force
run-yaml-force: FORCE=true
run-yaml-force: run-yaml

run-json-force: FORCE=true
run-json-force: run-json

run-prop-force: FORCE=true
run-prop-force: run-prop
