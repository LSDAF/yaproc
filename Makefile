include .make/*

default: help

.PHONY: help
help:
	@echo "YAPROC - Yet Another Properties Converter"
	@echo ""
	@echo "Build targets:"
	@echo "  make install     - Build the project (skipping tests)"
	@echo "  make clean      - Clean build artifacts"
	@echo "  make test       - Run tests"
	@echo "  make javadoc    - Generate javadoc"
	@echo ""
	@echo "Run targets:"
	@echo "  make run-yaml    - Convert to YAML format"
	@echo "  make run-json    - Convert to JSON format"
	@echo "  make run-prop    - Convert to Properties format"
	@echo ""
	@echo "Debug options:"
	@echo "  make run-debug   - Run with debug output"
	@echo "  make run-verbose - Run with verbose output"
	@echo ""
	@echo "For detailed run options:"
	@echo "  make run-help"
